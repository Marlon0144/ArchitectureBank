const API_BASE = '/api';

const Api = {
    async getCustomers() {
        const res = await fetch(`${API_BASE}/customers`);
        if (!res.ok) throw new Error('Error al obtener clientes');
        return res.json();
    },

    async getCustomerById(id) {
        const res = await fetch(`${API_BASE}/customers/${encodeURIComponent(id)}`);
        if (!res.ok) throw new Error('Cliente no encontrado');
        return res.json();
    },

    async createCustomer(data) {
        const res = await fetch(`${API_BASE}/customers`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });
        if (!res.ok) {
            const text = await res.text();
            throw new Error(text || 'Error al crear cliente');
        }
        return res.json();
    },

    async createTransfer(data) {
        const res = await fetch(`${API_BASE}/transactions`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });
        if (!res.ok) {
            const text = await res.text();
            throw new Error(text || 'Error en la transferencia');
        }
        return res.json();
    },

    async getTransactionsByAccount(accountNumber) {
        const res = await fetch(`${API_BASE}/transactions/${encodeURIComponent(accountNumber)}`);
        if (!res.ok) throw new Error('Error al obtener transacciones');
        return res.json();
    },
};
