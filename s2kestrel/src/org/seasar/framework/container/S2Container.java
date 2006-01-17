package org.seasar.framework.container;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * �R���|�[�l���g���Ǘ�����DI�R���e�i�̃C���^�[�t�F�[�X�ł��B
 *
 * @author higa
 */
public interface S2Container extends MetaDefAware {

	/**
     * �L�[���w�肵�ăR���|�[�l���g���擾���܂��B
     * �L�[��������̏ꍇ�A��v����R���|�[�l���g�������R���|�[�l���g��
     * �擾���܂��B
     * �L�[���N���X�܂��̓C���^�[�t�F�[�X�̏ꍇ�A
     * �u�R���|�[�l���g instanceof �L�[�v
     * �𖞂����R���|�[�l���g���擾���܂��B
     *
     * @param componentKey �R���|�[�l���g���擾���邽�߂̃L�[
     * @return �R���|�[�l���g
     * @throws ComponentNotFoundRuntimeException �R���|�[�l���g��������Ȃ��ꍇ
     * @throws TooManyRegistrationRuntimeException �������O�A�܂��͓����N���X�ɕ����̃R���|�[�l���g���o�^����Ă���ꍇ
     * @throws CyclicReferenceRuntimeException constructor injection�ŃR���|�[�l���g�̎Q�Ƃ��z���Ă���ꍇ
     */
	public Object getComponent(Object componentKey)
		throws
			ComponentNotFoundRuntimeException,
			TooManyRegistrationRuntimeException,
			CyclicReferenceRuntimeException;

	/**
     * �L�[���w�肵�ăR���|�[�l���g���������܂��B
     * �L�[��������̏ꍇ�A��v����R���|�[�l���g�������R���|�[�l���g��
     * �������܂��B
     * �L�[���N���X�܂��̓C���^�[�t�F�[�X�̏ꍇ�A
     * �u�R���|�[�l���g instanceof �L�[�v
     * �𖞂����R���|�[�l���g���������܂��B
     *
     * @param componentKey �R���|�[�l���g���擾���邽�߂̃L�[
     * @return �R���|�[�l���g�̔z��B�L�[�ɑΉ�����R���|�[�l���g�����݂��Ȃ��ꍇ�͋�̔z���Ԃ��܂��B
     * @throws CyclicReferenceRuntimeException constructor injection�ŃR���|�[�l���g�̎Q�Ƃ��z���Ă���ꍇ
     */
	public Object[] findComponents(Object componentKey)
		throws
			CyclicReferenceRuntimeException;

	/**
     * �O���R���|�[�l���g�ɃZ�b�^�[�E�C���W�F�N�V�����A���\�b�h�E�C��
     * �W�F�N�V���������s���܂��B
     * �O���R���|�[�l���g�̃N���X���L�[�Ƃ��ăR���|�[�l���g��`���擾���܂��B
     * instance���[�h��"outer"�ƒ�`���ꂽ�R���|�[�l���g�̂ݗL���ł��B
     * �u�R���|�[�l���g instanceof �O���R���|�[�l���g.getClass()�v
     * �𖞂����O���R���|�[�l���g��`�𗘗p���܂��B
     *
     * @param outerComponent �O���R���|�[�l���g
     * @throws ClassUnmatchRuntimeException �K������O���R���|�[�l���g��`��������Ȃ��ꍇ
     */
	public void injectDependency(Object outerComponent)
		throws ClassUnmatchRuntimeException;

	/**
     * �O���R���|�[�l���g�ɃZ�b�^�[�E�C���W�F�N�V�����A���\�b�h�E�C��
     * �W�F�N�V���������s���܂��B
     * componentClass���L�[�Ƃ��ăR���|�[�l���g��`���擾���܂��B
     * instance���[�h��"outer"�ƒ�`���ꂽ�R���|�[�l���g�̂ݗL���ł��B
     * �u�R���|�[�l���g instanceof �O���R���|�[�l���g�v
     * �𖞂����O���R���|�[�l���g��`�𗘗p���܂��B
     *
     * @param outerComponent �O���R���|�[�l���g
     * @param componentClass �O���R���|�[�l���g��`�̃L�[ (�N���X)
     * @throws ClassUnmatchRuntimeException �u�O���R���|�[�l���g instanceof �擾�����R���|�[�l���g�̃N���X�v��false��Ԃ��ꍇ
     */
	public void injectDependency(Object outerComponent, Class componentClass)
		throws ClassUnmatchRuntimeException;

	/**
     * �O���R���|�[�l���g�ɃZ�b�^�[�E�C���W�F�N�V�����A���\�b�h�E�C��
     * �W�F�N�V���������s���܂��B
     * componentClass���L�[�Ƃ��ăR���|�[�l���g��`���擾���܂��B
     * instance���[�h��"outer"�ƒ�`���ꂽ�R���|�[�l���g�̂ݗL���ł��B
     * �u�R���|�[�l���g instanceof �O���R���|�[�l���g�v
     * �𖞂����O���R���|�[�l���g��`�𗘗p���܂��B
     *
     * @param outerComponent �O���R���|�[�l���g
     * @param componentName �O���R���|�[�l���g��`�̃L�[ (���O)
     * @throws ClassUnmatchRuntimeException �u�O���R���|�[�l���g instanceof �擾�����R���|�[�l���g�̃N���X�v��false��Ԃ��ꍇ
     */
	public void injectDependency(Object outerComponent, String componentName)
		throws ClassUnmatchRuntimeException;

	/**
     * �I�u�W�F�N�g���R���|�[�l���g�Ƃ��ēo�^���܂��B
     * �L�[�̓I�u�W�F�N�g�̃N���X�ɂȂ�܂��B
     *
     * @param component �R���|�[�l���g�Ƃ��ēo�^����I�u�W�F�N�g
     */
	public void register(Object component);
	
	/**
     * �I�u�W�F�N�g�𖼑O�t���R���|�[�l���g�Ƃ��ēo�^���܂��B
     *
     * @param component �R���|�[�l���g�Ƃ��ēo�^����I�u�W�F�N�g
     * @param componentName �R���|�[�l���g��
     */
	public void register(Object component, String componentName);

	/**
     * �N���X���R���|�[�l���g��`�Ƃ��ēo�^���܂��B
     *
     * @param componentClass �R���|�[�l���g�̃N���X
     */
	public void register(Class componentClass);

	/**
     * �N���X�𖼑O�t���R���|�[�l���g��`�Ƃ��ēo�^���܂��B
     *
     * @param componentClass �R���|�[�l���g�̃N���X
     * @param componentName �R���|�[�l���g�̖��O
     */
	public void register(Class componentClass, String componentName);

	/**
     * �R���|�[�l���g��`��o�^���܂��B
     *
     * @param componentDef �o�^����R���|�[�l���g��`
     */
	public void register(ComponentDef componentDef);

	/**
     * �R���|�[�l���g��`�̐����擾���܂��B
     *
     * @return �R���|�[�l���g��`�̐�
     */
	public int getComponentDefSize();

	/**
     * �ԍ����w�肵�ăR���|�[�l���g��`���擾���܂��B
     *
     * @param index �ԍ�
     * @return �R���|�[�l���g��`
     */
	public ComponentDef getComponentDef(int index);

	/**
     * �w�肵���L�[�ɑΉ�����R���|�[�l���g��`���擾���܂��B
     *
     * @param componentKey �L�[
     * @return �R���|�[�l���g��`
     * @throws ComponentNotFoundRuntimeException �R���|�[�l���g��`��������Ȃ��ꍇ
     */
	public ComponentDef getComponentDef(Object componentKey)
		throws ComponentNotFoundRuntimeException;

	/**
     * �w�肵���L�[�ɑΉ�����R���|�[�l���g��`���������܂��B
     *
     * @param componentKey �L�[
     * @return �R���|�[�l���g��`�̔z��B�L�[�ɑΉ�����R���|�[�l���g�����݂��Ȃ��ꍇ�͋�̔z���Ԃ��܂��B
     */
	public ComponentDef[] findComponentDefs(Object componentKey);

	/**
     * �w�肵���L�[�ɑΉ�����R���|�[�l���g��`�����ǂ������肵�܂��B
     *
     * @param componentKey �L�[
     * @return ���݂���Ȃ�true
     */
	public boolean hasComponentDef(Object componentKey);
	
	/**
     * root�̃R���e�i�ŁApath�ɑΉ�����R���e�i�����Ƀ��[�h�����
     * ���邩��Ԃ��܂��B
     *
     * @param path �p�X
     * @return ���[�h����Ă���Ȃ�true
     */
	public boolean hasDescendant(String path);
	
	/**
     * root�̃R���e�i�ŁA�w�肵���p�X�ɑΉ����郍�[�h�ς݂̃R���e�i���擾���܂��B
     *
     * @param path �p�X
     * @return �R���e�i
     * @throws ContainerNotRegisteredRuntimeException �R���e�i��������Ȃ��ꍇ
     */
	public S2Container getDescendant(String path)
		throws ContainerNotRegisteredRuntimeException;
	
	/**
     * root�̃R���e�i�ɁA���[�h�ς݂̃R���e�i��o�^���܂��B
     *
     * @param descendant ���[�h�ς݂̃R���e�i
     */
	public void registerDescendant(S2Container descendant);

	/**
     * �q�R���e�i��include���܂��B
     *
     * @param child include����q�R���e�i
     */
	public void include(S2Container child);
	
	/**
     * �q�R���e�i�̐����擾���܂��B
     *
     * @return �q�R���e�i�̐�
     */
	public int getChildSize();
	
	/**
     * �ԍ����w�肵�Ďq�R���e�i���擾���܂��B
     *
     * @param index �q�R���e�i�̔ԍ�
     * @return �q�R���e�i
     */
	public S2Container getChild(int index);

	/**
     * �R���e�i�����������܂��B
     * �q�R���e�i�����ꍇ�A�q�R���e�i��S�ď�����������A���������������܂��B
     */
	public void init();

	/**
     * �R���e�i�̏I�������������Ȃ��܂��B
     * �q�R���e�i�����ꍇ�A�����̏I�����������s������A
     * �q�R���e�i�S�Ă̏I���������s���܂��B
     */
	public void destroy();
	
	/**
     * ���O��Ԃ��擾���܂��B
     *
     * @return ���O���
     */
	public String getNamespace();
	
	/**
     * ���O��Ԃ��Z�b�g���܂��B
     *
     * @param namespace �Z�b�g���閼�O���
     */
	public void setNamespace(String namespace);
	
	/**
     * �ݒ�t�@�C���̃p�X���擾���܂��B
     *
     * @return �ݒ�t�@�C���̃p�X
     */
	public String getPath();
	
	/**
     * �ݒ�t�@�C���̃p�X���Z�b�g���܂��B
     *
     * @param path �Z�b�g����ݒ�t�@�C���̃p�X
     */
	public void setPath(String path);

	/**
     * ���[�g�̃R���e�i���擾���܂��B
     *
     * @return ���[�g�̃R���e�i
     */
	public S2Container getRoot();
	
	/**
     * ���[�g�̃R���e�i���Z�b�g���܂��B
     *
     * @param root �Z�b�g���郋�[�g�̃R���e�i
     */
	public void setRoot(S2Container root);
	
	/**
     * ���N�G�X�g���擾���܂��B
     *
     * @return ���N�G�X�g
     */
	public HttpServletRequest getRequest();
	
	/**
     * ���N�G�X�g���Z�b�g���܂��B���N�G�X�g��TheadLocal�ŊǗ�����܂��B
     *
     * @param request �Z�b�g���郊�N�G�X�g
     */
	public void setRequest(HttpServletRequest request);
	
	/**
     * �Z�b�V�������擾���܂��B
     *
     * @return �Z�b�V����
     */
	public HttpSession getSession();
	
	/**
     * ���X�|���X���擾���܂��B
     *
     * @return ���X�|���X
     */
	public HttpServletResponse getResponse();
	
	/**
     * ���X�|���X���Z�b�g���܂��B���X�|���X��TheadLocal�ŊǗ�����܂��B
     *
     * @param response �Z�b�g���郌�X�|���X
     */
	public void setResponse(HttpServletResponse response);
	
	/**
     * �T�[�u���b�g�R���e�L�X�g���擾���܂��B
     *
     * @return �T�[�u���b�g�R���e�L�X�g
     */
	public ServletContext getServletContext();
	
	/**
     * �T�[�u���b�g�R���e�L�X�g���Z�b�g���܂��B
     *
     * @param servletContext �Z�b�g����T�[�u���b�g�R���e�L�X�g
     */
	public void setServletContext(ServletContext servletContext);
}
