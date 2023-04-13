async function getProducts() {
    const response = await fetch("/api/products");
    const products = await response.json();
    if (response.ok) {
        return products;
    } else {
        return response;
    }
}

async function getProductById() {
    const response = await fetch("/api/products/1234567890123");
    const product = await response.json();
    if (response.ok) {
        return product;
    } else {
        return response;
    }
}



const ProductsAPI = { getProducts, getProductById }
export default ProductsAPI;