package com.spring.boot.Config;
import com.spring.boot.Controller.Vm.security.UserVm;
import com.spring.boot.Exception.SystemException;
import com.spring.boot.Help.JwtToken;
import com.spring.boot.Service.Security.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class TokenHandler {
    @Autowired
    private UserService userService;
    private Duration time;
    private JwtBuilder jwtBilder;
    private JwtParser jwtParser;
    private String secretKey;



public TokenHandler(JwtToken jwtToken) {
    this.time = jwtToken.getTime();
    this.secretKey = jwtToken.getSecret();
    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    jwtBilder = Jwts.builder().signWith(key);
    jwtParser = Jwts.parserBuilder().setSigningKey(key).build();

}
public String CreateToken(UserVm uservm) {
    Date issueDate = new Date();
    Date expirationDate = Date.from(issueDate.toInstant().plus(time));
    jwtBilder.setSubject(uservm.getUsername());
    jwtBilder.setIssuedAt(issueDate);
    jwtBilder.setExpiration(expirationDate);
    jwtBilder.claim("Roles",uservm.getRolesVm().stream().map(vm-> vm.getCode()).collect(Collectors.toList()));
    return jwtBilder.compact();
}
    public UserVm validateToken(String token) {

            if (!jwtParser.isSigned(token)) {
                throw new com.spring.boot.Exception.SystemException("token.invalid", HttpStatus.UNAUTHORIZED);
            }

            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            Date issueDate = claims.getIssuedAt();
            Date expirationDate = claims.getExpiration();

            UserVm userVm = userService.getUserByUsername(username);
            if (userVm == null) {
                throw new com.spring.boot.Exception.SystemException("user.name.not.found", HttpStatus.NOT_FOUND);
            }
            if (expirationDate.before(new Date())) {
                throw new com.spring.boot.Exception.SystemException("token.expired", HttpStatus.UNAUTHORIZED);
            }
            if (!issueDate.before(expirationDate)) {
                throw new SystemException("token.issueDate.invalid", HttpStatus.BAD_REQUEST);
            }

            return userVm;
        }

    }


