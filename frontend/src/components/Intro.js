import React from "react";
const Intro = () => {
  return (
    <>
      <div>
        <div className="container py-5 my-5">
          <div className="row">
            <div className="col-md-6">
              <h1 className="text-primary fw-bold mb-4">
                Catalogue Management System for Internet Service Products
              </h1>
              <p className="lead mb-4">
                Welcome to the Catalogue Management System (CMS), your premier
                platform for efficient management and display of internet
                service products. Designed to meet the diverse needs of telecom
                giants like AT&T, Verizon, and T-Mobile, our CMS provides a
                robust solution for administrators and managers alike.
              </p>
            </div>
            <div className="col-md-6 d-flex justify-content-center">
              <img src="/intro.png" alt="Intro" width="400px" />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Intro;
