const basePath = '/api/professor';

const professor = {
  buscar: { method: 'GET', url: `${basePath}` },
  obter: { method: 'GET', url: `${basePath}/{id}` },
  inserir: { method: 'POST', url: `${basePath}` },
  alterar: { method: 'PUT', url: `${basePath}/{id}` },
  deletar: { method: 'DELETE', url: `${basePath}/{id}` },
  countByUnidade: { method: 'GET', url: `${basePath}/unidade/{id}/count` },
  countByAreaPesquisa: { method: 'GET', url: `${basePath}/area-pesquisa/{id}/count` },
};

export default resource => resource(`${basePath}`, {}, professor);
