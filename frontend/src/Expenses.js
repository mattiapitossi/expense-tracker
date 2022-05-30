import React, { Component } from "react";
import { Table, Container, Form, FormGroup, Input, Label, Button } from "reactstrap";
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import { Link } from "react-router-dom";
import AppNav from "./AppNav";
import Moment from "react-moment";

class Expenses extends Component {

  emptyItem = {
    description: "",
    expense_date: new Date(),
    location: "",
    value: 0,
    typeOfPayment: "CARD",
    typeOfTransaction: "OUT",
    category: {
      id: 2,
      name: "Utilities"
    },
    user: {
      id: 1,
      username: "admin",
      password: "1234"
    }
  }

  constructor(props) {
    super(props)

    this.state = {
      date: new Date(),
      isLoading: false,
      Categories: [],
      Expenses: [],
      item: this.emptyItem
    }

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleDateChange = this.handleDateChange.bind(this);
  }

  async handleSubmit(event) {

    const item = this.state.item;


    await fetch(`/api/expenses`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });

    event.preventDefault();
    this.props.history.push("/expenses");
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    let item = { ...this.state.item };
    item[name] = value;
    this.setState({ item });

    console.log(this.state);
  }

  handleDateChange(date) {
    let item = { ...this.state.item };
    item.expensedate = date;
    this.setState({ item });
  }

  async remove(id) {
    await fetch(`/api/expenses/${id}`, {
      method: "DELETE",
      headers: {
        "Accept": "application",
        "Content-Type": "application/json"
      }

    }).then(() => {
      let updateExpenses = [...this.state.Expenses].filter(i => i.id !== id);
      this.setState({ Expenses: updateExpenses })
    });

  }

  async componentDidMount() {
    const response = await fetch('/api/category');
    const body = await response.json();
    this.setState({ Categories: body, isLoading: false });

    const responseExp = await fetch('/api/expenses');
    const bodyExp = await responseExp.json();
    this.setState({ Expenses: bodyExp, isLoading: false });
  }

  render() {

    var styles = `
      .styled-table {
        border-collapse: collapse;
        margin: 25px 0;
        font-size: 0.9em;
        font-family: sans-serif;
        min-width: 400px;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
      }

      .styled-table thead tr {
        background-color: #4682B4;
        color: #ffffff;
        text-align: left;
      }   

      .styled-table th,
      .styled-table td {
        padding: 12px 15px;
      }

      .styled-table tbody tr {
        border-bottom: 1px solid #dddddd;
      }

      .styled-table tbody tr:nth-of-type(even) {
        background-color: #f3f3f3;
      }

      .styled-table tbody tr:last-of-type {
        border-bottom: 2px solid #4682B4;
      }

      .styled-table tbody tr.active-row {
        font-weight: bold;
        color: #4682B4;
      }

      /* The Modal (background) */
.modal {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}

/* Modal Header */
.modal-header {
  padding: 2px 16px;
  background-color: #4682B4;
  color: white;
}

/* Modal Body */
.modal-body {padding: 2px 16px;}

/* Modal Footer */
.modal-footer {
  padding: 2px 16px;
  background-color: #4682B4;
  color: white;
}

/* Modal Content */
.modal-content {
  position: relative;
  background-color: #fefefe;
  margin: auto;
  padding: 0;
  border: 1px solid #888;
  width: 80%;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
  animation-name: animatetop;
  animation-duration: 0.4s
}

/* Add Animation */
@keyframes animatetop {
  from {top: -300px; opacity: 0}
  to {top: 0; opacity: 1}
}
    `

    var styleSheet = document.createElement("style")
    styleSheet.innerText = styles
    document.head.appendChild(styleSheet)

    function init() {
      // Get the modal
      var modal = document.getElementById("myModal");

      // Get the button that opens the modal
      var btn = document.getElementById("myBtn");

      // Get the <span> element that closes the modal
      var span = document.getElementsByClassName("close")[0];

      // When the user clicks the button, open the modal 
      btn.onclick = function btnClick() {
        modal.style.display = "block";
      }

      // When the user clicks on <span> (x), close the modal
      span.onclick = function spanClick() {
        modal.style.display = "none";
      }

      // When the user clicks anywhere outside of the modal, close it
      window.onclick = function (event) {
        if (event.target == modal) {
          modal.style.display = "none";
        }
      }
    }


    document.addEventListener('readystatechange', function () {
      if (document.readyState === "complete") {
        init();
      }
    });



    const title = <h3>Add Expense</h3>
    const { Categories } = this.state;
    const { Expenses, isLoading } = this.state;

    if (isLoading)
      return (<div>Loading...</div>)

    let optionList =
      Categories.map(category =>
        <option value={category.id} key={category.id}>
          {category.name}
        </option>
      )

    let rows =
      Expenses.map(expense =>
        <tr key={expense.id}>
          <td>{expense.title}</td>
          <td>{expense.description}</td>
          <td>{expense.location}</td>
          <td><Moment date={expense.expensedate} format="YYYY/MM/DDThh:mm" /></td>
          <td>{expense.typeOfPayment}</td>
          <td>{expense.typeOfTransaction}</td>
          <td>{expense.category.name}</td>
          <td>{expense.value} €</td>
          <td><Button size="sm" color="danger" onClick={() => this.remove(expense.id)}>Delete</Button></td>
        </tr>
      )

    return (
      <div>
        <AppNav />



        {''}
        <Container>
          <center>
          <h3>Expense List</h3>


            <Button color="primary" id="myBtn">Open Modal</Button>
            </center>

            <div id="myModal" class="modal">


              <div class="modal-content">
                <div class="modal-body">
                  <span class="close">&times;</span>
                  <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                      <Label for="title">Title</Label>
                      <Input type="title" name="title" id="title" onChange={this.handleChange} autoComplete="name" />
                    </FormGroup>

                    <FormGroup>
                      <Label for="description">Description</Label>
                      <Input type="description" name="description" id="description" onChange={this.handleChange} autoComplete="name" />
                    </FormGroup>

                    <FormGroup>
                      <Label for="category" >Category</Label>
                      <select onChange={this.handleChange} name="category" id="category">
                        {optionList}
                      </select>
                    </FormGroup>

                    <FormGroup>
                      <Label for="date">Expense Date</Label>
                      <DatePicker selected={this.state.item.expense_date} onChange={this.handleDateChange} />
                    </FormGroup>

                    <FormGroup>
                      <Label for="location">Location</Label>
                      <Input type="text" name="location" id="location" onChange={this.handleChange} />
                    </FormGroup>

                    <FormGroup>
                      <Label for="typeOfTransaction">Type of Transaction</Label>
                      <select onChange={this.handleChange} name="typeOfTransaction" id="typeOfTransaction">
                        <option>Out</option>
                        <option>In</option>
                        <option>InOut</option>
                        <option>OutIn</option>
                      </select>
                    </FormGroup>

                    <FormGroup>
                      <Label for="typeOfPayment">Type of Payment</Label>
                      <select onChange={this.handleChange} id="typeOfPayment" name="typeOfPayment">
                        <option>Card</option>
                        <option>Cash</option>
                      </select>
                    </FormGroup>

                    <FormGroup>
                      <Label for="value">Value</Label>
                      <Input type="number" step="0.01" name="value" id="value" onChange={this.handleChange} />
                    </FormGroup>

                    <FormGroup>
                      <Button color="primary" type="submit">Save</Button>{' '}
                      <Button color="secondary" tag={Link} to="/categories">Cancel</Button>{' '}
                    </FormGroup>
                  </Form>
                </div>
              </div>
            </div>


          <table class="styled-table">
            <tbody>
              <tr class="active-row">
                <th width="10%%">Title</th>
                <th width="20%">Description</th>
                <th width="10%">Location</th>
                <th width="15%">Date</th>
                <th width="10%">Type Of Payment</th>
                <th width="10%">Type Of Transaction</th>
                <th>Category</th>
                <th>Value</th>
                <th width="10%">Action</th>
              </tr>
            </tbody>

            <tbody>
              {rows}
            </tbody>

          </table>
        </Container>
      </div>
    );
  }
}




export default Expenses;
