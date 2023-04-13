import './App.css';
import {useState} from "react";
import Button from '@mui/material/Button';
import {FolderList, ProfileList} from "./components/List";
import ProductsAPI from "./api/products";
import ProfilesAPI from "./api/profiles";

function App() {
    const [products, setProducts] = useState([]);
    const [profile, setProfile] = useState([]);
    const handleGetProducts = async () => {
        await ProductsAPI.getProducts().then((res) => setProducts(res))
    }
    const handleGetProductById = async () => {
        await ProductsAPI.getProductById().then((res) => setProducts([res]))
    }
    const handleGetProfileByEmail = async () => {
        await ProfilesAPI.getProfileByEmail().then((res) => setProfile(res))
    }
  return (
    <div className="App">
        <p>Hello ReactJS!</p>
        <div className="products-container">
            <Button variant="contained" onClick={  () => {handleGetProducts().then()}  }>Get Products</Button>
            <Button variant="contained" onClick={  () => {handleGetProductById().then()}  }>Get Product By Id</Button>
            <FolderList products={products}></FolderList>
        </div>
        <div className="profiles-container">
            <Button variant="contained" onClick={  () => {handleGetProfileByEmail().then()}  }>Get Profile By Email</Button>
            <ProfileList profile={profile}></ProfileList>
        </div>
    </div>
  );
}

export default App;
