import './App.css'
import {useEffect, useState} from "react";
import axios from "axios";

type GitHubUser = {
    username: string,
    avatarUrl: string,
}

export default function App() {
    const [gitHubUser, setGitHubUser] = useState<GitHubUser | undefined>(undefined)

    function fetchUsername() {
        axios.get("/api/auth/me").then((res) => {
            setGitHubUser(res.data)
        });
    }

    useEffect(() => {
        fetchUsername()
    }, [])

    function showProfile() {
        if (!gitHubUser) return (<p>Please login to see Credentials</p>);
        return (
            <div>
                <h1>{gitHubUser?.username}</h1>
                <img src={gitHubUser.avatarUrl} alt={gitHubUser.username + " avatar"}/>
            </div>
        );
    }

    return (
        <>
            {
                !gitHubUser ?
                    <a href={"http://localhost:8080/oauth2/authorization/okta"}>Login</a> :
                    <a href={"http://localhost:8080/logout"}>Logout</a>
            }
            <br/>
            {showProfile()}
        </>
    )
}
