import * as React from "react";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import ImageIcon from "@mui/icons-material/Image";

export default function Product(props) {
  return (
    <div>
      {props.product.map((p) => (
        <ListItem>
          <ListItemAvatar>
            <Avatar>
              <ImageIcon />
            </Avatar>
          </ListItemAvatar>
          <ListItemText primary={p.name} secondary={p.ean ? p.ean : p.statusText} />
        </ListItem> 
      ))}
    </div>
  );
}
