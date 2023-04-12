async function getProducts() {
    const response = await fetch("/api/products");
    const products = await response.json();
    if (response.ok) {
        return products;
    } else {
        return response;
    }
}

const API = { getProducts }
export default API;