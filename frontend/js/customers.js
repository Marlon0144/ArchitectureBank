const Customers = {
    init() {
        document.getElementById('create-customer-form').addEventListener('submit', e => {
            e.preventDefault();
            this.createCustomer();
        });
        document.getElementById('refresh-customers').addEventListener('click', () => this.loadTable());
        this.loadTable();
    },

    async loadTable() {
        const tbody = document.querySelector('#customers-table tbody');
        const emptyMsg = document.getElementById('customers-empty');
        tbody.innerHTML = '';
        emptyMsg.hidden = true;

        try {
            const customers = await Api.getCustomers();
            if (customers.length === 0) {
                emptyMsg.hidden = false;
                return;
            }
            customers.forEach(c => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${c.id}</td>
                    <td>${this.escapeHtml(c.firstName)}</td>
                    <td>${this.escapeHtml(c.lastName)}</td>
                    <td>${this.escapeHtml(c.accountNumber)}</td>
                    <td>$${Number(c.balance).toLocaleString('es-CO', { minimumFractionDigits: 2 })}</td>
                `;
                tbody.appendChild(tr);
            });
        } catch (err) {
            this.showMsg('customer-msg', err.message, 'error');
        }
    },

    async createCustomer() {
        const data = {
            firstName: document.getElementById('firstName').value.trim(),
            lastName: document.getElementById('lastName').value.trim(),
            accountNumber: document.getElementById('accountNumber').value.trim(),
            balance: parseFloat(document.getElementById('balance').value),
        };

        try {
            const created = await Api.createCustomer(data);
            this.showMsg('customer-msg', `Cliente "${created.firstName} ${created.lastName}" creado con éxito.`, 'success');
            document.getElementById('create-customer-form').reset();
            this.loadTable();
        } catch (err) {
            this.showMsg('customer-msg', err.message, 'error');
        }
    },

    showMsg(id, text, type) {
        const el = document.getElementById(id);
        el.textContent = text;
        el.className = `msg ${type}`;
        el.hidden = false;
        setTimeout(() => { el.hidden = true; }, 5000);
    },

    escapeHtml(str) {
        const div = document.createElement('div');
        div.textContent = str;
        return div.innerHTML;
    },
};
