import React from "react";

export default class Book extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.book.isbn}</td>
                <td>{this.props.book.title}</td>
                <td>{this.props.book.author.lastName + ' ' + this.props.book.author.firstName}</td>
                <td>{this.props.book.genre.name}</td>
                <td>{this.props.book.publishYear}</td>
            </tr>
        )
    }
}