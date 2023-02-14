import {useState, useEffect} from "react";
import SouvenirService from "../../API/SouvenirService";
import Loader from "../../UI/Loader/Loader";
import SouvenirItem from "./SouvenirItem";


const SouvenirList = () => {
    const [souvenirs, setSouvenirs] = useState([]);
    const [loading, setLoading] = useState(false);
    useEffect(() => {
        getAll();
    }, []);

    const getAll = () => {
        setLoading(true);
        setTimeout(() => {
            SouvenirService.getAll().then(data => setSouvenirs(data));
            setLoading(false);
        }, 1000);
    }
    const removeSouvenir = (id) => {
        SouvenirService.remove(id);
        setSouvenirs(souvenirs.filter(s => s.id !== id));
    }
    const updateSouvenir = (souvenir) => {
        setSouvenirs([...souvenirs.filter(s => s.id !== souvenir.id), souvenir]);
    }
    return (
        <div>
            {
                loading ? (<div>
                    <Loader/>
                </div>) : (<div>
                        {souvenirs.length ? (
                            <div>
                                {souvenirs.map(souvenir =>
                                    <div key={souvenir.id}>
                                        <SouvenirItem souvenir={souvenir}
                                                      removeSouvenir={removeSouvenir}
                                                      updateSouvenir={updateSouvenir}/>
                                    </div>)}
                            </div>) : (
                            <div>
                                <h1> souvenirs not founded! </h1>
                            </div>
                        )}
                    </div>
                )}
        </div>
    )
}
export default SouvenirList;