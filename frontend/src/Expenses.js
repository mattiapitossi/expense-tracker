import React, { Component } from "react";
import { Table, Container, Form, FormGroup, Input, Label, Button } from "reactstrap";
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import {Link} from "react-router-dom";
import AppNav from "./AppNav";
import Moment from "react-moment";

class Expenses extends Component {

  emptyItem = {
    description : "",
    expense_date : new Date(),
    location : "",
    value: 0,
    typeOfPayment: "",
    typeOfTransaction: "",
    category : {
      id : 1, 
      name : "Food"
    },
    user: {
      id : 1, 
      username : "admin",
      password : "1234"
    }
  }

  constructor(props) {
    super(props)

    this.state = {
      date: new Date(),
      isLoading : false,
      Categories : [],
      Expenses : [],
      item : this.emptyItem
    }

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleDateChange = this.handleDateChange.bind(this);
  }

  async handleSubmit(event){
     
    const item = this.state.item;
  

    await fetch(`/api/expenses`, {
      method : 'POST',
      headers : {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body : JSON.stringify(item),
    });
    
    event.preventDefault();
    this.props.history.push("/expenses");
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});

    console.log(this.state);
  }

  handleDateChange(date) {
    let item = {...this.state.item};
    item.expensedate = date;
    this.setState({item});
  }

  async remove(id) {
    await fetch(`/api/expenses/${id}` , {
      method : "DELETE",
      headers : {
       "Accept" : "application",
       "Content-Type" : "application/json"
      }

    }).then( () => {
      let updateExpenses = [...this.state.Expenses].filter(i => i.id !== id);
      this.setState({Expenses : updateExpenses})
    });

  }

  async componentDidMount() {
    const response = await fetch('/api/category');
    const body = await response.json();
    this.setState({Categories : body , isLoading : false});

    const responseExp = await fetch('/api/expenses');
    const bodyExp = await responseExp.json();
    this.setState({Expenses : bodyExp , isLoading : false});
  }

  render() {
    const title = <h3>Add Expense</h3>
    const {Categories} = this.state;
    const {Expenses, isLoading} = this.state;

    if(isLoading) 
      return(<div>Loading...</div>)

    let optionList = 
      Categories.map( category =>
        <option value = {category.id} key = {category.id}> 
          {category.name}
        </option>
      )

    let rows = 
      Expenses.map( expense => 
        <tr key = {expense.id}>
          <td>{expense.description}</td>
          <td>{expense.location}</td>
          <td><Moment date = {expense.expensedate} format = "YYYY/MM/DD"/></td>
          <td><Button size = "sm" color = "danger" onClick={ () => this.remove(expense.id) }>Delete</Button></td>
        </tr>
        )

    return (
      <div>
        <AppNav/>

        <Container>
          {title}

          <Form onSubmit = {this.handleSubmit}>
            <FormGroup>
              <Label for="description">Title</Label>
              <Input type="description" name="description" id="description" onChange={this.handleChange} autoComplete="name"/>       
            </FormGroup>

            <FormGroup>
              <Label for="category" >Category</Label>
                <select onChange={this.handleChange} name = "category" id = "category">
                  {optionList}
                </select>
            </FormGroup>

            <FormGroup>
              <Label for = "date">Expense Date</Label>
              <DatePicker selected = {this.state.item.expensedate} onChange={this.handleDateChange}/>
            </FormGroup>

            <FormGroup>
              <Label for = "location">Location</Label>
              <Input type = "text" name = "location" id = "location" onChange={this.handleChange}/>
            </FormGroup>

            <FormGroup>
              <Label for = "typeOfTransaction">Type of Transaction</Label>
              <select onChange={this.handleChange}  name = "typeOfTransaction" id = "typeOfTransaction">
                  <option>In</option>
                  <option>Out</option>
                  <option>InOut</option>
                  <option>OutIn</option>
              </select>
            </FormGroup>

            <FormGroup>
              <Label for = "typeOfPayment">Type of Payment</Label>
              <select onChange={this.handleChange} id = "typeOfPayment" name = "typeOfPayment">
                  <option>Cash</option>
                  <option>Card</option>
              </select>
            </FormGroup>

            <FormGroup>
              <Label for = "value">Money</Label>
              <Input type="number" step="0.01" name = "value" id = "value" onChange={this.handleChange}/>
            </FormGroup>

            <FormGroup>
              <Button color = "primary" type = "submit">Save</Button>{' '}
              <Button color = "secondary" tag = {Link} to = "/categories">Cancel</Button>{' '}
            </FormGroup>
          </Form>
        </Container>

        {''}
        <Container>
          <h3>Expense List</h3>

          <table className = "mt-4">
          <tbody>
              <tr>
                <th width = "50%">Description</th>
                <th width = "10%">Location</th>
                <th>Date</th>
                <th>Category</th>
                <th width = "10%">Action</th>
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
