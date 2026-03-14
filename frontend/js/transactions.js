const Transactions = {
    init() {
        document.getElementById('transfer-form').addEventListener('submit', e => {
            e.preventDefault();
            this.doTransfer();
        });
        document.getElementById('history-form').addEventListener('submit', e => {
            e.preventDefault();
            this.loadHistory();
        });
    },

    async doTransfer() {
        const data = {
            senderNumberAccount: document.getElementById('senderAccount').value.trim(),
            receiverNumberAccount: document.getElementById('receiverAccount').value.trim(),
            amount: parseFloat(document.getElementById('amount').value),
        };

        try {
            const tx = await Api.createTransfer(data);
            this.showMsg('transfer-msg',
                `Transferencia exitosa: $${Number(tx.amount).toLocaleString('es-CO', { minimumFractionDigits: 2 })} de ${this.escapeHtml(tx.senderNumberAccount)} a ${this.escapeHtml(tx.receiverNumberAccount)}.`,
                'success');
            document.getElementById('transfer-form').reset();
        } catch (err) {
            this.showMsg('transfer-msg', err.message, 'error');
        }
    },

    async loadHistory() {
        const accountNumber = document.getElementById('historyAccount').value.trim();
        const resultsCard = document.getElementById('history-results');
        const tbody = document.querySelector('#transactions-table tbody');
        const emptyMsg = document.getElementById('transactions-empty');

        tbody.innerHTML = '';
        emptyMsg.hidden = true;
        resultsCard.hidden = false;

        try {
            const transactions = await Api.getTransactionsByAccount(accountNumber);
            if (transactions.length === 0) {
                emptyMsg.hidden = false;
                return;
            }
            transactions.forEach(tx => {
                const tr = document.createElement('tr');
                const dateStr = tx.timestamp
                    ? new Date(tx.timestamp).toLocaleString('es-CO')
                    : '—';
                tr.innerHTML = `
                    <td>${tx.id}</td>
                    <td>${this.escapeHtml(tx.senderNumberAccount)}</td>
                    <td>${this.escapeHtml(tx.receiverNumberAccount)}</td>
                    <td>$${Number(tx.amount).toLocaleString('es-CO', { minimumFractionDigits: 2 })}</td>
                    <td>${dateStr}</td>
                `;
                tbody.appendChild(tr);
            });
        } catch (err) {
            this.showMsg('transfer-msg', err.message, 'error');
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
