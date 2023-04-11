import './App.css';
import {useEffect, useState} from "react";

function App() {
  const [products, setProducts] = useState([]);
  useEffect(() => {
    fetch('http://localhost:8080/API/products/')
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          setProducts(data);
        })
        .catch((err) => {
          console.log(err.message);
            setProducts(err.message)
        });
  }, []);
  return (
    <div className="App">
        <p>Hello ReactJS!</p>
        <div className="products-container">
            {products}
        </div>
    </div>
  );
}

export default App;
