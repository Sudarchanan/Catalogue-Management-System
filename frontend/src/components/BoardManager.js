import React, { useState } from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFileInvoiceDollar,
  faEye,
  faSearch,
  faList,
} from "@fortawesome/free-solid-svg-icons";
import "./BoardManager.css"; // Make sure to create and include your custom CSS for any additional styling

const BoardManager = () => {
  return (
    <div className="container-fluid fade-in">
      {/* Apply fade-in animation */}
      <div className="row">
        <div className="col-md-2 sidebar">
          <header className="jumbotron slide-in">
            {/* Apply slide-in animation */}
            <h2 className="text-center mb-4">Manager Dashboard</h2>
          </header>
          <ul className="nav flex-column">
            <li className="nav-item">
              <Link to="/create-quotation" className="nav-link">
                <FontAwesomeIcon icon={faFileInvoiceDollar} /> Create Quotation
              </Link>
            </li>
            <li className="nav-item">
              <Link to="/get-quotation" className="nav-link">
                <FontAwesomeIcon icon={faEye} /> View Quotation
              </Link>
            </li>
            <li className="nav-item">
              <Link to="/view-product-by-name-mgr" className="nav-link">
                <FontAwesomeIcon icon={faSearch} /> View Product by Name
              </Link>
            </li>
            <li className="nav-item">
              <Link to="/view-all-products-mgr" className="nav-link">
                <FontAwesomeIcon icon={faList} /> View All Products
              </Link>
            </li>
          </ul>
        </div>
        <div className="col-md-10">
          <div className="content mt-5">
            <h3>Welcome to the Manager Dashboard</h3>
            <p>Select an option from the sidebar to get started.</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BoardManager;
