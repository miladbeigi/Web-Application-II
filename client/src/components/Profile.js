import * as React from "react";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import ImageIcon from "@mui/icons-material/Image";

export default function Profile(props) {
  return (
    <div>
      {props.profile.map((p) => (
        <ListItem>
          <ListItemAvatar>
            <Avatar>
              <ImageIcon />
            </Avatar>
          </ListItemAvatar>
          <ListItemText primary={  p.name == undefined ? p.status : p.name +" "+p.lastname} secondary={p.email ? p.email : p.statusText} />
        </ListItem> 
      ))}
    </div>
  );
}
