import React from "react";
const About = () => {
  return (
    <div>
      <div className="container py-5 my-5">
        <div className="row">
          <div className="col-md-6">
            <h1 className="text-primary fw-bold mb-4">About Us</h1>
            <p className="lead mb-4">
              At CMS, our mission is to streamline and optimize the management
              of wireline and wireless internet service products. We aim to
              empower telecom companies with the tools they need to effectively
              handle their product offerings, enhancing operational efficiency
              and customer satisfaction.
            </p>
          </div>
          <div className="col-md-6 d-flex justify-content-center">
            <img src="/about.png" alt="about" height="400px" />
          </div>
        </div>
      </div>
    </div>
  );
};

export default About;
