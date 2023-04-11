import './App.css';
import {useEffect, useState} from "react";
import Button from '@mui/material/Button';
import FolderList from "./components/List";
import API from "./api/products";

function App() {
    const [products, setProducts] = useState([]);
    useEffect(() => {
        async function loadProducts() {
            const loadedProducts = await API.getProducts();
            setProducts(loadedProducts);
        } loadProducts();
    }, []);
  return (
    <div className="App">
        <p>Hello ReactJS!</p>
        <div className="products-container">
            <Button variant="contained">Get Products</Button>
            <FolderList products={products}></FolderList>
        </div>
    </div>
  );
}

export default App;
