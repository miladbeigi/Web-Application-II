async function getProfileByEmail(text) {
    const response = await fetch("/api/profiles/"+text);
    const profile = await response.json();
    if (response.ok) {
        return profile;
    } else {
        return response;
    }
}

async function addProfile(profile) {
    const response = await fetch("/api/profiles", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(profile),
      });
      if (response.ok) {
        return response;
      } else {
        return response;
      }
}

async function updateProfile(profile) {
  const response = await fetch("/api/profiles/" + profile.email , {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(profile),
    });
    if (response.ok) {
      return response;
    } else {
      return response;
    }
}


const ProfilesAPI = { getProfileByEmail , addProfile, updateProfile}
export default ProfilesAPI;