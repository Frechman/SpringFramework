import React from "react";

export default class Header extends React.Component {

    constructor(props) {
        super(props);
        this.state = {}
    }

    render() {
        return (
            <h3 className="text-center">{this.props.title}</h3>
        )
    }
}