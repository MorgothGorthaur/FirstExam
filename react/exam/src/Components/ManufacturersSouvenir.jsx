import {useState} from "react";
import {useEffect} from "react";
import ManufacturerService from "../API/ManufacturerService";
import Loader from "../UI/Loader/Loader";

const ManufacturersSouvenir = ({id}) => {
    const [souvenirs, setSouvenirs] = useState([]);
    const [loading, setLoading] = useState(false);
    useEffect(() => {
        getAll();
    }, []);

    const getAll = () => {
        setLoading(true);
        setTimeout(() => {
            ManufacturerService.getManufacturersSouvenirs(id).then(data => setSouvenirs(data));
            setLoading(false);
        }, 1000);
    }
    return (
        <div>
            {
                loading ? (
                    <Loader/>
                ) : (
                    <div>
                        {
                            souvenirs.length ? (
                                <div>
                                    souvenirs.map(souvenir => {
                                    <h1> gg</h1>
                                })
                                </div>
                            ) : (
                                <h1> souvenirs not founded! </h1>
                            )
                        }
                    </div>
                )
            }
        </div>
    );
};
export default ManufacturersSouvenir;