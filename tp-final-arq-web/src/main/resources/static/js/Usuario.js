class Usuario extends CRUDGenerico {
    constructor() {
        super('usuarios/');
        this.container = document.querySelector('.main');
    }
	
	getAuthentication(authentication) {
		console.log(authentication);
		let token = authentication.token;
		if(authentication.status === "Success") {
			console.log(typeof token);
			this.getHome(token);
		}
	}
	
	async getHome(token) {
		let url = this.BASEURI + '/' + "home/";
		console.log(url);
		const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json',
				'Authorization' : token
            },
//            body: JSON.stringify(data)
        });
		if (response.ok) {
			let res = await response;
            res = await res.text();
			console.log(res);
			return res;
		}
 		if (!response.ok) {
            const error = await response.text();
            return Promise.reject(error);
        }
	}
}