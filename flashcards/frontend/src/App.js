import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class LoginForm extends Component {

    handleSignIn(e) {
        e.preventDefault()
        let email = this.refs.email.value;
        let pswd = this.refs.pswd.value;
        this.props.onSignIn(email, pswd)
    }

    render() {
        return (
            <form onSubmit={this.handleSignIn.bind(this)}>
                <h3>Sign in</h3>
                <input type="text" ref="email" placeholder="username" />
                <input type="password" ref="pswd" placeholder="password" />
                <input type="submit" value="Login" />
            </form>
        )
    }

}


class App extends Component {

    constructor(props) {
        super(props)
        // the initial application state
        this.state = {
            user: null
        }
    }

    // App "actions" (functions that modify state)
    signIn(email, pswd) {
        // This is where you would call Firebase, an API etc...
        // calling setState will re-render the entire app (efficiently!)
        this.setState({
            user: {
                email,
                pswd,
            }
        })
    }

    signOut() {
        // clear out user from state
        this.setState({user: null})
    }

    render() {
        // Here we pass relevant state to our child components
        // as props. Note that functions are passed using `bind` to
        // make sure we keep our scope to App
        return (
            <div>
                <h1>My cool App</h1>
                {

                        <LoginForm
                    onSignIn={this.signIn.bind(this)}
                        />
                }
            </div>
        )

    }

}

export default  App
