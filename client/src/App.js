import './App.css';
import {useState} from "react";
import Button from '@mui/material/Button';
import FolderList from "./components/List";
import API from "./api/products";

function App() {
    const [products, setProducts] = useState([]);
    const handleGetProducts = async () => {
        await API.getProducts().then((res) => setProducts(res))
    }
  return (
    <div className="App">
        <p>Hello ReactJS!</p>
        <div className="products-container">
            <Button variant="contained" onClick={  () => {handleGetProducts().then()}  }>Get Products</Button>
            <FolderList products={products}></FolderList>
        </div>
    </div>
  );
}

export default App;
