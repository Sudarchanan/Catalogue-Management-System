import React, { useState, useEffect } from "react";
import UserService from "../services/userService";

const ViewAllQuotation = () => {
  const [quotations, setQuotations] = useState([]);
  const [error, setError] = useState(null);
  const [productsMap, setProductsMap] = useState({});

  useEffect(() => {
    const fetchQuotations = async () => {
      try {
        const response = await UserService.getallquotations();
        setQuotations(response.data);

        // Extract unique product IDs from quotationProduct
        const productIds = [
          ...new Set(
            response.data.flatMap((quotation) =>
              quotation.quotationProduct.map((product) => product.id)
            )
          ),
        ];

        // Fetch product names
        const productPromises = productIds.map((productId) =>
          UserService.getproductbyidmgr(productId).then((response) => ({
            id: productId,
            name: response.data.name,
          }))
        );

        // Resolve all promises
        const products = await Promise.all(productPromises);
        const productsMap = products.reduce((map, product) => {
          map[product.id] = product.name;
          return map;
        }, {});

        setProductsMap(productsMap);
      } catch (error) {
        console.error("Error fetching quotations:", error);
        setError("Error fetching quotations. Please try again later.");
      }
    };

    fetchQuotations();
  }, []);

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-10">
          <div className="card p-4 rounded">
            <h2 className="mb-4">All Quotations</h2>
            <table className="table table-striped">
              <thead>
                <tr>
                  <th>Quotation ID</th>
                  <th>Customer Name</th>
                  <th>Product Name</th>
                  <th>Quantity</th>
                  <th>Total Amount</th>
                  <th>Discount</th>
                </tr>
              </thead>
              <tbody>
                {quotations.map((quotation) =>
                  quotation.quotationProduct.map((product) => (
                    <tr key={product.id}>
                      <td>{quotation.id}</td>
                      <td>{quotation.customerName}</td>
                      <td>{productsMap[product.id]}</td>
                      <td>{quotation.quantity}</td>
                      <td>{quotation.totalAmount}</td>
                      <td>{quotation.discount}</td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
            {error && <p className="text-danger mt-3">{error}</p>}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewAllQuotation;
