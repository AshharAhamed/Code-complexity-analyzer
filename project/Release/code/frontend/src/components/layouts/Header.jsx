import React, { Component } from 'react' ;

class Header extends Component{
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <header>
                    <h2>Code Complexity Analyzer</h2>
                </header>
            </div>
        )
    }
}

export default Header ;