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
            books: [],
            authors: [],
            genres: []
        };
    }

    handleUpdateBook = (book) => {
        this.setState({isLoaded: false});
        fetch(root + "/books/update/", {
            method: 'PUT',
            body: JSON.stringify(book),
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then((result) => {
                console.log('updated');
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
        Promise.all([
            fetch(root + '/books'),
            fetch(root + '/books/attributes/')
        ])
            .then(([res1, res2]) => Promise.all([res1.json(), res2.json()]))
            .then(([books, bookDto]) =>
                this.setState({
                    books,
                    attributes: Object.keys(bookDto).filter(key => key !== 'id'),
                    isLoaded: true
                }));
    };

    loadAuthorsAndGenresFromServer = () => {
        Promise.all([
            fetch(root + '/authors'),
            fetch(root + '/genres')
        ])
            .then(([res1, res2]) => Promise.all([res1.json(), res2.json()]))
            .then(([authors, genres]) => this.setState({authors, genres}));
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
                            <BookList books={books}
                                      attributes={this.state.attributes}
                                      handleDeleteBook={this.handleDeleteBook}
                                      handleUpdateBook={this.handleUpdateBook}/>

                            <FormCreateBook attributes={this.state.attributes}
                                            handleCreateBook={this.handleCreateBook}/>
                        </div>
                    </div>
                </React.Fragment>
            )
        }
    }
}