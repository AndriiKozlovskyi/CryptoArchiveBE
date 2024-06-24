package crypto_archive.com.api.controllers.public_path;

import crypto_archive.com.api.requests.AuthRequest;
import crypto_archive.com.api.requests.RegisterRequest;
import crypto_archive.com.api.responses.AuthResponse;
import crypto_archive.com.api.services.AuthService;
import crypto_archive.com.api.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${base-path}/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    AuthService service;
    @Autowired
    JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/isTokenExpired")
    public ResponseEntity<?> validateToken(@RequestBody AuthResponse token) {
        try {
            if(jwtService.isTokenExpired(token.getToken())) {
                return new ResponseEntity<>("{\"expired\"" + ":" + true + "}", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("{\"expired\"" + ":" + false + "}", HttpStatus.OK);
    }
}
