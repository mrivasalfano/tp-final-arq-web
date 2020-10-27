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

        if (!response.ok) {
            const error = await response.text();
            return Promise.reject(error);
        }
    }
}