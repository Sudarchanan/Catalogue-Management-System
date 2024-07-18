import React, { useState } from "react";
import { Link } from "react-router-dom";

const UserBoard = () => {
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  return (
    <div className="container-fluid fade-in">
      {" "}
      {/* Apply fade-in animation */}
      <div className="row justify-content-center mt-5">
        <div className="col-md-6">
          <header className="jumbotron slide-in">
            {" "}
            {/* Apply slide-in animation */}
            <h2 className="text-center mb-4">User Dashboard</h2>
            <div className="form-group mt-3">
              <label htmlFor="actions">Actions:</label>
              <div className="dropdown">
                <button
                  className="btn btn-primary dropdown-toggle"
                  type="button"
                  id="dropdownMenuButton"
                  onClick={toggleDropdown}
                >
                  Select Action
                </button>
                <ul
                  className={`dropdown-menu${dropdownOpen ? " show" : ""}`}
                  aria-labelledby="dropdownMenuButton"
                >
                  <li>
                    <Link to="/view-all-products" className="dropdown-item">
                      View All Products
                    </Link>
                  </li>
                  <li>
                    <Link to="/view-products-by-name" className="dropdown-item">
                      View Products by Name
                    </Link>
                  </li>
                </ul>
              </div>
            </div>
          </header>
        </div>
      </div>
    </div>
  );
};

export default UserBoard;
