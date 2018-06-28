const basePath = '/api/area-pesquisa';

const areaPesquisa = {
  buscar: { method: 'GET', url: `${basePath}` },
  obter: { method: 'GET', url: `${basePath}/{id}` },
  inserir: { method: 'POST', url: `${basePath}` },
  alterar: { method: 'PUT', url: `${basePath}/{id}` },
  deletar: { method: 'DELETE', url: `${basePath}/{id}` },
};

export default resource => resource(`${basePath}`, {}, areaPesquisa);
