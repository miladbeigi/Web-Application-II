package wa2.lab2.server.profiles

import org.springframework.stereotype.Service

@Service
class ProfileServiceImp (private val profileRepository: ProfileRepository) : ProfileService {

    override fun getProfileByEmail(email: String): ProfileDTO? {
        return profileRepository.findById(email).map { it.toDTO() }.orElse(null)
    }
}