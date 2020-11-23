class CRUDGenerico {
    constructor(resource) {
        this.RESOURCE = resource;
        this.BASEURI = 'http://localhost:8080';
    }

    async add(data) {
        return this.postModular('', data);
    }

    async getAll() {
        return this.getModular('');
    }

    async getModularAuthorization(uri,token) {
        const response = await fetch(this.BASEURI + "/" + this.RESOURCE + uri, {
			method: 'GET', 
			headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json',
				'Authorization' : token
            }
		});

        if (response.ok)
            return await response.json();
        else {
            const errorMessage = await response.json();
            return Promise.reject(errorMessage);
        }
    }

	async getModular(uri) {
        const response = await fetch(this.BASEURI + "/" + this.RESOURCE + uri);

        if (response.ok)
            return await response.json();
        else {
            const errorMessage = await response.text();
            return Promise.reject(errorMessage);
        }
    }

    async postModular(uri, data) {
        const response = await fetch(this.BASEURI + '/' + this.RESOURCE + uri, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json'
            },
            body: JSON.stringify(data)
        });
	    if (response.ok) {
            return await response.json();		
		} else {
            const error = await response.json();
            return Promise.reject(error);
        }
    }

	async postModularAuthorization(uri, data,token) {
        const response = await fetch(this.BASEURI + '/' + this.RESOURCE + uri, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json',
				'Authorization' : token
            },
            body: JSON.stringify(data)
        });
	    if (response.ok) {
            return await response.json();		
		} else {
            const error = await response.json();
            return Promise.reject(error);
			//return 'error';
        }
    }

	async postFormModularAuthorization(uri, data,token) {
        const response = await fetch(this.BASEURI + '/' + this.RESOURCE + uri, {
            method: 'POST',
            headers: {
				'Authorization' : token
            },
            body: data
        });
	    if (response.ok) {
            return await response.json();		
		} else {
            return Promise.reject(response);
        }
    }
}