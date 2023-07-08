package de.lendmove.lendmoveapi.controller;

import java.util.*;
import java.util.stream.Collectors;

import de.lendmove.lendmoveapi.entity.EnumRole;
import de.lendmove.lendmoveapi.entity.Role;
import de.lendmove.lendmoveapi.entity.User;
import de.lendmove.lendmoveapi.payload.LoginRequest;
import de.lendmove.lendmoveapi.payload.RoleRequest;
import de.lendmove.lendmoveapi.payload.SignupRequest;
import de.lendmove.lendmoveapi.payload.response.JwtResponse;
import de.lendmove.lendmoveapi.payload.response.MessageResponse;
import de.lendmove.lendmoveapi.repo.RoleRepository;
import de.lendmove.lendmoveapi.repo.UserRepository;
import de.lendmove.lendmoveapi.security.jwt.JwtUtils;
import de.lendmove.lendmoveapi.security.services.UserDetailsImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        String tarif = userRepository.findByUsername(userDetails.getUsername()).get().getTarif();

    //    String phonenumber = userRepository.findByUsername(userDetails.getUsername()).get().getPhonenumber();

        String birthday = userRepository.findByUsername(userDetails.getUsername()).get().getBirthday();
        String streetNameAndNumber = userRepository.findByUsername(userDetails.getUsername()).get().getStreetNameAndNumber();


        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                tarif,
                userDetails.getBirthday(),
                userDetails.getPhonenumber(),
                userDetails.getPostalCode(),
                userDetails.getStreetNameAndNumber(),
                userDetails.getCountry(),
                userDetails.getCity(),
                userDetails.getFirstname(),
                userDetails.getUsername(),
                userDetails.getCreatedAt(),
                userDetails.getStatus()
                 ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        if (userRepository.existsByPhonenumber(signUpRequest.getPhonenumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Phonenummer is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        user.setCity(signUpRequest.getCity());
        user.setPhonenumber(signUpRequest.getPhonenumber());
        user.setCheckpassword(signUpRequest.getCheckpassword());
        user.setPostalCode(signUpRequest.getPostalCode());
        user.setCountry(signUpRequest.getCountry());
        user.setFirstname(signUpRequest.getFirstname());
        user.setBirthday(signUpRequest.getBirthday());
        user.setSurname(signUpRequest.getSurname());
        user.setStreetNameAndNumber(signUpRequest.getStreetNameAndNumber());
        user.setStatus("CREATED");
        user.setCreatedAt(new Date().toString());
        user.setTarif(signUpRequest.getTarif());

        Set<String> strRoles = signUpRequest.getRole();

        Set<Role> roles = new HashSet<>();
        

            if (strRoles == null) {
                Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(EnumRole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);

                            break;
                        case "worker":
                            Role modRole = roleRepository.findByName(EnumRole.ROLE_WORKER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);

                            break;
                        default:
                            Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);
            if (signUpRequest.getCheckpassword().equals(signUpRequest.getPassword()) ) {

        userRepository.save(user);
        } else {
            return ResponseEntity.ok(new MessageResponse("Passwort stimmen nicht überein !"));

        }
        System.out.println(user + "am present  -----------");
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('WORKER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody SignupRequest user) {

       User updatedUser = new User();
        if(user.getId()!=null && userRepository.existsById(user.getId()))
        {
            updatedUser = userRepository.findById(user.getId()).get();


                    if(userRepository.existsByUsername(user.getUsername()))
                    {
                        User usernew =  modifiedUserValue(user, updatedUser);
                        /*

                           if(usernew.getCheckpassword().equals(usernew.getPassword())){
                               userRepository.saveAndFlush(usernew);
                               return ResponseEntity.ok(new MessageResponse("User "+usernew.getUsername()+" updated successfully!"));
                           }else
                           {
                               return ResponseEntity.ok(new MessageResponse("User "+usernew.getUsername()+" Password stimmen nicht!"));
                           }

                         */
                        userRepository.saveAndFlush(usernew);
                        return ResponseEntity.ok(new MessageResponse("User "+usernew.getUsername()+" updated successfully!"));

                    }else
                    {
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("Error: Username dont exist."));
                    }
                }

        else
        {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: The id doesn't exist or the value is null."));
        }

         }

    @PostMapping("/update/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> modifyRole(@PathVariable("username") String username, @RequestBody RoleRequest roleRequest)
    {
        try{
            User user = userRepository.findByUsername(username).get();
            Set<Role> roles = new HashSet<>();
            if(username!=null && user !=null)
            {
                roles = changeRole(roleRequest.getRole());
                 if(!roles.isEmpty())
                 {
                     user.setRoles(roles);
                     userRepository.saveAndFlush(user);
                     return ResponseEntity.ok(new MessageResponse("User "+user.getUsername()+" role has been  successful updated!"));
                 }
            }else
            {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: The username doesn't exist or the value is null."));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: The username doesn't exist or the value is null."));
        }


        return  null;
    }

    @GetMapping
    @PreAuthorize("hasRole('WORKER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "username") String sortBy, @RequestParam(defaultValue = "asc") String sortDir)
    {
        Map<String, Object> response = new HashMap<>();
        try{
            List<User> users = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size,
                    sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
            Page<User> pageUsers = null;
            pageUsers = userRepository.findAll(paging);
            users = pageUsers.getContent();

            response.put("users", users);
            response.put("currentPage", pageUsers.getNumber());
            response.put("totalItems", pageUsers.getTotalElements());
            response.put("totalPages", pageUsers.getTotalPages());
            response.put("size", pageUsers.getSize());
            response.put("sort", pageUsers.getSort());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
            response.put("error","Error by getting all users.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }




    private User modifiedUserValue(SignupRequest user, User updatedUser)
    {

        if(user.getEmail()!= null)
        {
            if(!userRepository.existsByEmail(user.getEmail()))
            {
                updatedUser.setEmail(user.getEmail());
            }

        }
        if(user.getPassword()!=null)
        {
            updatedUser.setPassword(encoder.encode(user.getPassword()));
        }

        if(user.getCity() !=null)
        {
            updatedUser.setCity(user.getCity());
        }
        if(user.getCountry() != null)
        {
            updatedUser.setCountry(user.getCountry());
        }
        if(user.getPhonenumber() != null)
        {
            updatedUser.setPhonenumber(user.getPhonenumber());
        }
        if(user.getPostalCode() != null)
        {
            updatedUser.setPostalCode(user.getPostalCode());
        }
        if(user.getStreetNameAndNumber() != null)
        {
            updatedUser.setStreetNameAndNumber(user.getStreetNameAndNumber());
        }
        if(user.getFirstname()!=null)
        {
            updatedUser.setFirstname(user.getFirstname());
        }
        if(user.getSurname() !=null)
        {
            updatedUser.setSurname(user.getSurname());
        }
        if(user.getUsername() !=null && !userRepository.existsByEmail(user.getUsername()))
        {
            updatedUser.setUsername(user.getUsername());
        }

        if(user.getTarif() !=null)
        {
            updatedUser.setTarif(user.getTarif());
        }

        if(user.getBirthday()!=null)
        {
            updatedUser.setBirthday(user.getBirthday());
        }

        if(user.getCheckpassword()!=null )
        {
            updatedUser.setCheckpassword(user.getCheckpassword());
        }


        return updatedUser;
    }

    private Set<Role> changeRole (Set<String> roleRequest)
    {
        Set<Role> roles = new HashSet<>();

        roleRequest.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(EnumRole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                    break;
                case "worker":
                    Role modRole = roleRepository.findByName(EnumRole.ROLE_WORKER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
            }
        });


        return roles;

    }



        @PostMapping("/logout")
        public ResponseEntity<?> logout() {

            return ResponseEntity.ok("Logout successful");
        }



    public ResponseEntity<?> forgotPassword(@Valid @RequestBody SignupRequest user) {
        User updatedUser = new User();
        if(user.getId()!=null && userRepository.existsById(user.getId()))
        {
            updatedUser = userRepository.findByEmail(user.getEmail()).get();


            if(userRepository.existsByUsername(user.getUsername()))
            {
                User usernew =  modifiedUserValue(user, updatedUser);


                userRepository.saveAndFlush(usernew);
                return ResponseEntity.ok(new MessageResponse("User "+usernew.getUsername()+" updated successfully!"));


            }else
            {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username dont exist."));
            }
        }

        else
        {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: The id doesn't exist or the value is null."));
        }


    }

    }

