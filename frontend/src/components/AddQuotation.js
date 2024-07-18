import React, { useState, useEffect } from "react";
import UserService from "../services/userService";

const AddQuotation = () => {
  const [products, setProducts] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [customerName, setCustomerName] = useState("");
  const [quantity, setQuantity] = useState(1);
  const [discount, setDiscount] = useState(0);
  const [totalAmount, setTotalAmount] = useState(0);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await UserService.getallProductForQuotation(); // Fetch all products
        setProducts(response.data); // Assuming response contains an array of products
      } catch (error) {
        console.error("Error fetching products:", error);
        setError("Error fetching products. Please try again later.");
      }
    };

    fetchProducts();
  }, []);

  const handleProductChange = (event) => {
    const selectedProductIndex = event.target.value;
    setSelectedProduct(products[selectedProductIndex]);
  };

  const handleCustomerNameChange = (event) => {
    setCustomerName(event.target.value);
  };

  const handleQuantityChange = (event) => {
    setQuantity(parseInt(event.target.value));
  };

  const handleDiscountChange = (event) => {
    setDiscount(parseFloat(event.target.value));
  };

  const handleTotalAmountChange = (event) => {
    setTotalAmount(parseFloat(event.target.value));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = {
      customerName: customerName,
      products: [
        {
          productName: selectedProduct.name,
          features: selectedProduct.features.map((feature) => ({
            featureName: feature.name,
            parameters: feature.parameters,
          })),
        },
      ],
      quantity: quantity,
      discount: discount,
      totalAmount: totalAmount,
    };

    console.log("Form Data:", formData);

    try {
      await UserService.addquotation(formData);
      // Handle successful quotation creation
    } catch (error) {
      console.error("Error adding quotation:", error);
      setError("Error adding quotation. Please try again later.");
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4">Add Quotation</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="customerName">Customer Name:</label>
          <input
            type="text"
            className="form-control"
            id="customerName"
            value={customerName}
            onChange={handleCustomerNameChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="product">Product:</label>
          <select
            className="form-control"
            id="product"
            value={products.indexOf(selectedProduct)}
            onChange={handleProductChange}
          >
            <option value="">Select a product</option>
            {products.map((product, index) => (
              <option key={product.id} value={index}>
                {product.name}
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="quantity">Quantity:</label>
          <input
            type="number"
            className="form-control"
            id="quantity"
            value={quantity}
            onChange={handleQuantityChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="discount">Discount:</label>
          <input
            type="number"
            className="form-control"
            id="discount"
            value={discount}
            onChange={handleDiscountChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="totalAmount">Total Amount:</label>
          <input
            type="number"
            className="form-control"
            id="totalAmount"
            value={totalAmount}
            onChange={handleTotalAmountChange}
          />
        </div>
        <button type="submit" className="btn btn-primary btn-block">
          Add Quotation
        </button>
      </form>
    </div>
  );
};

export default AddQuotation;
