package wa.lab.server.security
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy


@Configuration
@EnableWebSecurity
class SecurityConfig(private val keycloakLogoutHandler: KeycloakLogoutHandler) {
    @Bean
    protected fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http.authorizeHttpRequests()
            .requestMatchers("/api/expert/add", "/api/manager/add", "/api/ticket/*", "/api/profiles/*")
            .hasRole("Manager")
            .requestMatchers("/api/ticket/start/*", "/api/ticket/stop/*", "/api/ticket/reopen/*", "/api/ticket/close/*", "/api/ticket/resolve/*")
            .hasRole("Expert")
            .requestMatchers("/api/ticket/create")
            .hasRole("Client")
            .anyRequest()
            .permitAll()

        http.oauth2Login()
            .loginPage("/login-oauth2")
            .and()
            .logout()
            .addLogoutHandler(keycloakLogoutHandler)
            .logoutSuccessUrl("/")

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.oauth2ResourceServer { obj: OAuth2ResourceServerConfigurer<HttpSecurity?> -> obj.jwt() }
        http.cors().and().csrf().disable()
        return http.build()
    }
}

