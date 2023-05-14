package wa.lab.server.profiles

import org.springframework.stereotype.Service
import wa.lab.server.profiles.exceptions.ProfileAlreadyExistsException
import wa.lab.server.profiles.exceptions.ProfileNotFoundException

@Service
class ProfileServiceImp(private val profileRepository: ProfileRepository) : ProfileService {

    override fun getProfileByEmail(email: String): ProfileDTO? {
        val profile: ProfileDTO? = profileRepository.findById(email).map { it.toDTO() }.orElse(null)
        if (profile == null) {
            throw ProfileNotFoundException("Profile with email $email not found")
        } else {
            return profile
        }
    }

    override fun createProfile(email: String, name: String, lastname: String): ProfileDTO {
        if (profileRepository.existsById(email)) {
            throw ProfileAlreadyExistsException("Profile with email $email already exists")
        }
        val savedProfile = profileRepository.save(Profile(email, name, lastname))
        println("Profile created: ${savedProfile.email}")
        return savedProfile.toDTO()
    }

    override fun updateProfile(email: String, name: String, lastname: String): ProfileDTO {
        val profile = profileRepository.findById(email).orElseThrow { ProfileNotFoundException("Profile with email $email not found") }
        profile.name = name
        profile.lastname = lastname
        val savedProfile = profileRepository.save(profile)
        println("Profile updated: ${savedProfile.email}")
        return savedProfile.toDTO()
    }
}