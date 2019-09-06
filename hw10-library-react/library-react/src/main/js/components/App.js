import React from "react";
import BookList from "./BookList";
import Header from "./Header";
import FormCreateBook from "./FormCreateBook";

const root = '/api/v1';

export default class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            books: []
        };
    }

    // handleEditBook = (book) => {
    //     this.setState({isLoaded: false});
    //     fetch(root + "/books/edit/", {
    //         method: 'PUT',
    //         body: JSON.stringify(book)
    //     })
    //         .then(response => response.json())
    //         .then((result) => {
    //             this.loadListBookFromServer();
    //         }, (error) => {
    //             console.log(error);
    //             this.setState({
    //                 isLoaded: true,
    //                 error
    //             });
    //         })
    //         .catch((error) => {
    //             console.log(error);
    //             this.setState({
    //                 isLoaded: true,
    //                 error
    //             })
    //         })
    // };

    handleDeleteBook = (book) => {
        this.setState({isLoaded: false});
        fetch(root + "/books/delete/", {
            method: 'DELETE',
            body: book.isbn
        })
            .then((result) => {
                console.log('removed');
                this.loadListBookFromServer();
            }, (error) => {
                console.log(error);
                this.setState({
                    isLoaded: true,
                    error
                });
            })
            .catch((error) => {
                console.log(error);
                this.setState({
                    isLoaded: true,
                    error
                })
            })
    };

    handleCreateBook = (newBook) => {
        this.setState({isLoaded: false});
        fetch(root + "/books/add/", {
            method: 'POST',
            body: JSON.stringify(newBook),
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then((result) => {
                console.log('added');
                this.loadListBookFromServer();
            }, (error) => {
                console.log(error);
                this.setState({
                    isLoaded: true,
                    error
                });
            })
            .catch((error) => {
                console.log(error);
                this.setState({
                    isLoaded: true,
                    error
                })
            })
    };

    loadListBookFromServer = () => {
        fetch(root + '/books')
            .then(response => response.json())
            .then(books => this.setState({books, isLoaded: true}));
        fetch(root + '/books/attributes/')
            .then(response => response.json())
            .then(book =>
                this.setState({attributes: Object.keys(book).filter(key => key !== 'id')}));
    };

    componentDidMount() {
        this.loadListBookFromServer();
    }

    render() {
        const {error, isLoaded, books} = this.state;
        if (error) {
            return (
                <div className="alert alert-danger" role="alert">
                    <strong>Ошибка!</strong> {error.message}
                    {alert("Error!" + error.message)}
                </div>
            );
        } else if (!isLoaded) {
            return <div>Загрузка...</div>;
        } else {
            return (
                <React.Fragment>
                    <Header title={'Library'}/>
                    <div className="container">
                        <div className="starter-template">
                            <BookList books={books} handleDeleteBook={this.handleDeleteBook}/>

                            <FormCreateBook attributes={this.state.attributes}
                                            handleCreateBook={this.handleCreateBook}/>
                        </div>
                    </div>
                </React.Fragment>
            )
        }
    }
}