import React, { useState, useEffect } from "react";
import UserService from "../services/userService";
import UpdateProduct from "./UpdateProduct";

const UpdateProductForm = () => {
  const [productId, setProductId] = useState("");
  const [product, setProduct] = useState(null);
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);
  const [showUpdateForm, setShowUpdateForm] = useState(false);
  const [responseMessage, setResponseMessage] = useState("");

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await UserService.getall();
        if (response.data) {
          setProducts(response.data);
        } else {
          setProducts([]);
        }
      } catch (error) {
        console.error("Error fetching products:", error);
        setProducts([]);
        setError("Failed to fetch products.");
      }
    };

    fetchProducts();
  }, []);

  const handleProductIdChange = (e) => {
    setProductId(e.target.value);
    setProduct(null); // Reset product details when selecting a new product
    setShowUpdateForm(false); // Hide update form until product details are fetched
    setResponseMessage(""); // Clear any previous response messages
    setError(null); // Clear any previous errors
  };

  const handleFormSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await UserService.getproductbyidadmin(productId);
      if (response.data) {
        setProduct(response.data);
        setShowUpdateForm(true);
        setError(null);
        setResponseMessage("Product details fetched successfully.");
      } else {
        setProduct(null);
        setShowUpdateForm(false);
        setError("Product not found.");
        setResponseMessage("");
      }
    } catch (error) {
      console.error("Error fetching product:", error);
      setProduct(null);
      setShowUpdateForm(false);
      if (error.response && error.response.status === 404) {
        setError("Product not found.");
      } else {
        setError("An unexpected error occurred.");
      }
      setResponseMessage("Error fetching product. Please try again.");
    }
  };

  const handleProductUpdate = async (updatedProduct) => {
    try {
      await UserService.updateproduct(updatedProduct);
      setShowUpdateForm(false);
      setResponseMessage("Product updated successfully.");
    } catch (error) {
      console.error("Error updating product:", error);
      setError("Error updating product");
      setResponseMessage("Error updating product. Please try again.");
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card p-4 rounded">
            <h2 className="mb-4">Update Product and Features</h2>
            <form onSubmit={handleFormSubmit}>
              <div className="form-group">
                <label htmlFor="productName">Select Product:</label>
                <select
                  id="productName"
                  className="form-control"
                  value={productId}
                  onChange={handleProductIdChange}
                  required
                >
                  <option value="">Select a product</option>
                  {products.map((prod) => (
                    <option key={prod.id} value={prod.id}>
                      {prod.name}
                    </option>
                  ))}
                </select>
              </div>
              <button type="submit" className="btn btn-primary">
                Get Product Details
              </button>
            </form>
            {error && <p className="mt-4 text-danger">{error}</p>}
            {responseMessage && <p className="mt-4">{responseMessage}</p>}
            {showUpdateForm && product && (
              <UpdateProduct
                product={product}
                productId={productId}
                onProductUpdate={handleProductUpdate}
                products={products} // Pass products to UpdateProduct for dropdowns
              />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default UpdateProductForm;
