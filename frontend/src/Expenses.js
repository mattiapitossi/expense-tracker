import React, { Component } from "react";
import { Table, Container, Form, FormGroup, Input, Label, Button } from "reactstrap";
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import {Link} from "react-router-dom";
import AppNav from "./AppNav";

class Expenses extends Component {

  emptyItem = {
    id : "103",
    expensedate : new Date(),
    description : "",
    location : "",
    categories : [1, "Travel"]
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
        <option id = {category.id} > 
          {category.name}
        </option>
      )

    let rows = 
      Expenses.map( expense => 
        <tr>
          <td>{expense.description}</td>
          <td>{expense.location}</td>
          <td>{expense.expensedate}</td>
          <td>{expense.category.name}</td>
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
              <label for = "title">Title</label>
              <input type = "text" name = "title" id = "title" onChange={this.handleChange}/>
            </FormGroup>

            <FormGroup>
              <label for = "category">Category</label>
              <select>
                {optionList}
              </select>
              <input type = "text" name = "category" id = "category" onChange={this.handleChange}/>
            </FormGroup>

            <FormGroup>
              <label for = "date">Expense Date</label>
              <DatePicker selected = {this.state.date} onChange={this.handleChange}/>
            </FormGroup>

            <FormGroup>
              <label for = "location">Location</label>
              <input type = "text" name = "location" id = "location" onChange={this.handleChange}/>
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

          <Table className = "mt-4">

            <thread>
              <tr>
                <th width = "50%">Description</th>
                <th width = "10%">Location</th>
                <th>Date</th>
                <th>Category</th>
                <th width = "10%">Action</th>
              </tr>
            </thread>

            <tbody>
              {rows}
            </tbody>

          </Table>
        </Container>
      </div>
    );
  }
}

export default Expenses;
