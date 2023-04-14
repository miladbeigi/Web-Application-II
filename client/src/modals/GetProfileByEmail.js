import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useState } from "react";
import Profile from '../components/Profile';
import ProfilesAPI from '../api/profiles';

export default function GetProductById() {
  const [open, setOpen] = React.useState(false);
  const [profile, setProfile] = useState([]);
  const [text, setText] = useState("");

  const handleClickOpen = () => {
    setOpen(true);
    setProfile([]);
  };

  const handleClose = () => {
    setText("");
    setProfile([]);
    setOpen(false);
  };

  const handleGetProfileByEmail = async () => {
    await ProfilesAPI.getProfileByEmail(text).then((res) => setProfile([res]));
  };

  return (
    <div>
      <Button variant="contained" onClick={handleClickOpen}>
        Get Profile
      </Button>
      <Dialog fullWidth open={open} onClose={handleClose}>
        <DialogTitle>Get profile by email</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Email Address"
            fullWidth
            variant="standard"
            onChange={(ev) => {
              setText(ev.target.value);
            }}
            />
            <Profile profile={profile}/>
            <DialogContentText>
            </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={()=>{handleGetProfileByEmail().then();}}>Get</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}