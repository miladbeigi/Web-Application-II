import "./App.css";
import { useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import { FolderList, ProfileList } from "./components/List";
import ProductsAPI from "./api/products";
import ProfilesAPI from "./api/profiles";
import Header from "./components/Header";
import GetProfileByEmail from "./modals/GetProfileByEmail";
import GetProductById from "./modals/GetProductById";
import AddProfile from "./modals/AddProfile";
import UpdateProfile from "./modals/UpdateProfile";

function App() {
  const [products, setProducts] = useState([]);
  const [profile, setProfile] = useState([]);
  const handleGetProducts = async () => {
    await ProductsAPI.getProducts().then((res) => setProducts(res));
  };
  const handleGetProductById = async () => {
    await ProductsAPI.getProductById().then((res) => setProducts([res]));
  };
  const handleGetProfileByEmail = async () => {
    await ProfilesAPI.getProfileByEmail().then((res) => setProfile(res));
  };
  return (
    <Router>
      <Grid container direction="column">
        <Grid item>
          <Header />
        </Grid>
        <Grid item></Grid>
      </Grid>
      <Grid container marginLeft={1} spacing={1} style={{ marginTop: 10 }}>
        <Grid item>
          <Button
            variant="contained"
            onClick={() => {
              handleGetProducts().then();
            }}
          >
            Get Products
          </Button>
        </Grid>
        <Grid item>
          <GetProductById></GetProductById>
        </Grid>
        <Grid item>
          <GetProfileByEmail></GetProfileByEmail>
        </Grid>
        <Grid item>
          <AddProfile></AddProfile>
        </Grid>
        <Grid item>
          <UpdateProfile></UpdateProfile>
        </Grid>
      </Grid>
      <Grid container direction="column">
        <FolderList products={products} />
      </Grid>
    </Router>
  );
}

export default App;
