import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Product from '../components/Product'
import ProductsAPI from '../api/products'
import { useState } from "react";

export default function GetProductById() {
  const [open, setOpen] = React.useState(false);
  const [product, setProduct] = useState([]);
  const [text, setText] = useState("");

  const handleClickOpen = () => {
    setOpen(true);
    setProduct([]);
  };

  const handleClose = () => {
    setText("");
    setProduct([]);
    setOpen(false);
  };

  const handleGetProductById = async () => {
    await ProductsAPI.getProductById(text).then((res) => setProduct([res]));
  };

  return (
    <div>
      <Button variant="contained" onClick={handleClickOpen}>
        Get Product
      </Button>
      <Dialog fullWidth open={open} onClose={handleClose}>
        <DialogTitle>Get product by EAN</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="EAN Number"
            fullWidth
            variant="standard"
            onChange={(ev) => {
              setText(ev.target.value);
            }}
            />
            <Product product={product}/>
            <DialogContentText>
            </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={()=>{handleGetProductById(text).then();}}>Get</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}