async function getProducts() {
    const response = await fetch("/api/products");
    const products = await response.json();
    if (response.ok) {
        return products;
    } else {
        return response.json();
    }
}

async function getProductById(text) {
    const response = await fetch("/api/products/" + text);
    const product = await response.json();
    if (response.ok) {
        return product;
    } else {
        return response;
    }
}



const ProductsAPI = { getProducts, getProductById }
export default ProductsAPI;