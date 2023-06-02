package wa.lab.server.profiles

import io.micrometer.observation.annotation.Observed
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@Observed
@RestController
class ProfileController(private val profileService: ProfileService) {
    @GetMapping("/api/profiles/{email}")
    fun getProfileByEmail(@PathVariable email: String): ProfileDTO? {
        return profileService.getProfileByEmail(email)
    }

    @PostMapping(
        value = ["/api/profiles"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun createProfile(@RequestBody @Valid profile: ProfileDTO): ProfileDTO {
        return profileService.createProfile(profile.email, profile.name, profile.lastname)
    }

    @PutMapping(
        value = ["/api/profiles/{email}"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun updateProfile(@PathVariable email: String, @RequestBody @Valid profile: ProfileDTO): ProfileDTO {
        return profileService.updateProfile(email, profile.name, profile.lastname)
    }
}