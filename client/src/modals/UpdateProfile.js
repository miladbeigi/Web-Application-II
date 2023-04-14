import * as React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import { useState } from "react";
import ProfilesAPI from "../api/profiles";
import Profile from "../components/Profile";

export default function GetProductById() {
  const [open, setOpen] = React.useState(false);
  const [profile, setProfile] = useState([]);
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [family, setFamily] = useState("");

  const handleClickOpen = () => {
    setOpen(true);
    setProfile([]);
  };

  const handleClose = () => {
    setFamily("");
    setEmail("");
    setName("");
    setProfile([]);
    setOpen(false);
  };

  const handleUpdateProfile = async () => {
    const p = {
      email: email,
      name: name,
      lastname: family,
    };
    await ProfilesAPI.updateProfile(p).then((res) => setProfile([res]));
  };

  return (
    <div>
      <Button variant="contained" onClick={handleClickOpen}>
        Update Profile
      </Button>
      <Dialog fullWidth open={open} onClose={handleClose}>
        <DialogTitle>Update user profile</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Email"
            fullWidth
            variant="standard"
            onChange={(ev) => {
              setEmail(ev.target.value);
            }}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="First Name"
            fullWidth
            variant="standard"
            onChange={(ev) => {
              setName(ev.target.value);
            }}
          />
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Last Name"
            fullWidth
            variant="standard"
            onChange={(ev) => {
              setFamily(ev.target.value);
            }}
          />
          <Profile profile={profile} />
          <DialogContentText></DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button
            onClick={() => {
              handleUpdateProfile().then();
            }}
          >
            Update
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
