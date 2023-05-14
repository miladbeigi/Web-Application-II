package wa.lab.server.profiles

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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