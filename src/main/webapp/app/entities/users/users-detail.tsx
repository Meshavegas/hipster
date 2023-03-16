import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './users.reducer';

export const UsersDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const usersEntity = useAppSelector(state => state.users.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="usersDetailsHeading">
          <Translate contentKey="myApp.users.detail.title">Users</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="myApp.users.id">Id</Translate>
            </span>
          </dt>
          <dd>{usersEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="myApp.users.name">Name</Translate>
            </span>
          </dt>
          <dd>{usersEntity.name}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="myApp.users.email">Email</Translate>
            </span>
          </dt>
          <dd>{usersEntity.email}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="myApp.users.password">Password</Translate>
            </span>
          </dt>
          <dd>{usersEntity.password}</dd>
          <dt>
            <span id="createAt">
              <Translate contentKey="myApp.users.createAt">Create At</Translate>
            </span>
          </dt>
          <dd>{usersEntity.createAt ? <TextFormat value={usersEntity.createAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateAt">
              <Translate contentKey="myApp.users.updateAt">Update At</Translate>
            </span>
          </dt>
          <dd>{usersEntity.updateAt ? <TextFormat value={usersEntity.updateAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createBy">
              <Translate contentKey="myApp.users.createBy">Create By</Translate>
            </span>
          </dt>
          <dd>{usersEntity.createBy}</dd>
          <dt>
            <span id="updateBy">
              <Translate contentKey="myApp.users.updateBy">Update By</Translate>
            </span>
          </dt>
          <dd>{usersEntity.updateBy}</dd>
        </dl>
        <Button tag={Link} to="/users" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/users/${usersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UsersDetail;
