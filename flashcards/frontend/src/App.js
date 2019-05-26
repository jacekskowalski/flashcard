import React, { Component } from 'react';
import './App.css';
import '../node_modules/font-awesome/css/font-awesome.min.css'
import Footer from './Layouts/Footer/Footer'
import Main from './Layouts/Main'


class App extends Component {

  
  render() {
    
    console.log(this.props.keys)
    
    return (
      <>
      

      <main>
      <Main></Main>
      </main>




<footer><Footer></Footer></footer>
      
      
      </>
    );
  }
}

export default App;
