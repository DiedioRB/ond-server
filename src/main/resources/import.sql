INSERT INTO users (id, name, email, password) VALUES (998, 'Andre', 'andre.ribas@ufpr.br', '0eb83259f402f3ffe62608fb21fed6b67e4653c728939449bd6f9279fbf271d1');
INSERT INTO users (id, name, email, password) VALUES (999, 'Andre 2', 'andre.ribas2@ufpr.br', '0eb83259f402f3ffe62608fb21fed6b67e4653c728939449bd6f9279fbf271d1');

INSERT INTO items (id, name, kind, parent_id, owner_id) VALUES (900, 'Quarto', 'ROOM', null, 998);
INSERT INTO items (id, name, kind, parent_id, owner_id) VALUES (901, 'Sala', 'ROOM', null, 999);
INSERT INTO items (id, name, kind, parent_id, owner_id) VALUES (902, 'Livro', 'ITEM', 900, 998);
INSERT INTO items (id, name, kind, parent_id, owner_id) VALUES (903, 'Armazenamento grande', 'ROOM', 900, 999);