async function getProfileByEmail() {
    const response = await fetch("/api/profiles/milad.be@gmail.com");
    const profile = await response.json();
    console.log(response)
    if (response.ok) {
        return profile;
    } else {
        return response;
    }
}

const ProfilesAPI = { getProfileByEmail }
export default ProfilesAPI;