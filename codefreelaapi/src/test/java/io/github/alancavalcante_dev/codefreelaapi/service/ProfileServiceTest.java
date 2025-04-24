package io.github.alancavalcante_dev.codefreelaapi.service;

//@SpringBootTest
//public class ProfileServiceTest {
//
//    @Autowired
//    private ProfileRepository profileRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserService userService;
//
//
//
//
//    @Test
//    public void saveProfile() {
//        Optional<User> optionalUser = userService.getUserById("e9c3e0ed-7765-4a8e-a002-6d29a664e4e0");
//        if (optionalUser.isEmpty()) new RuntimeException("Usuário não encontrado");
//        User user = optionalUser.get();
//
//        Profile profile = new Profile();
//        profile.setIdProfile(UUID.randomUUID());
//        profile.setName("Alan Beterraba");
//        profile.setEmail(user.getEmail());
//        profile.setCpf("12345678901");
//        profile.setPj(false);
//        profile.setUser(user);
//        profileRepository.save(profile);
//
//        user.setActive(true);
//        userRepository.save(user);
//
//        log.info("Perfil criado: {}", profile);
//    }
//}
